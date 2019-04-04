[参考网站]https://my.oschina.net/happyBKs?tab=newest&catalogId=436091  
一： 应用场景
1. 海量数据分析引擎
2. 站内搜索引擎（Github使用ES搜索它们1000多亿行代码）
3. 数据仓库（不建议使用es作为数据库）

二： 安装使用
1. 下载并解压  
项目使用Elasticsearch版本为5.6.8,下载路径为https://artifacts.elastic.co/downloads/elasticsearch/elasticsearch-5.6.8.zip，建议使用迅雷下载
2. 目录说明
    * bin:存放ES的脚本命令
    * config:存放配置文件。如果要修改某个配置，原出厂配置文件一定要备份一份
    * lib:存放依赖的jar包等。可以看到Elasticsearch 5.6.8所对应的Lucene等的版本
    * plugin:存放第三方插件
    * module:存放一些模块的目录
3. 启动elasticsearch  
    i. 修改config目录下的jvm.options文件，找到-Dfile.encoding设置为GBK编码  
    ```
    -Dfile.encoding=GBK
    ```
    ii.进入bin目录双击elasticsearch.bat，出现以下显示说明启动成功
    ```
    [2019-03-27T16:45:47,358][INFO ][o.e.n.Node               ] [14lgLEK] started
    ```
    此时访问 http://127.0.0.1:9200/ 会显示如下字符串
    ```
       {
         "name" : "14lgLEK",
         "cluster_name" : "elasticsearch",
         "cluster_uuid" : "WBBzkO7sQ9W8Kqn0eZweaA",
         "version" : {
           "number" : "5.6.8",
           "build_hash" : "688ecce",
           "build_date" : "2018-02-16T16:46:30.010Z",
           "build_snapshot" : false,
           "lucene_version" : "6.6.1"
         },
         "tagline" : "You Know, for Search"
       } 
    ```
4. 安装header（Elasticsearch可视化）插件，必须要有nodeJS环境  
    i. 使用git下载,如果没有git可以直接下载zip包
    ```
        cd /d 你的目录
        git clone https://github.com/mobz/elasticsearch-head.git
    ```
    下载完后cd到下级目录,执行npm install
    ```
      cd /d 你的目录/elasticsearch-head
      npm install 
    ```
    如果出现
    ```
        found 33 vulnerabilities (19 low, 8 moderate, 6 high)
    ```
    运行
    ```
        npm audit fix
    ```
    新建run.bat，内容为 npm run start，双击启动，访问127.0.0.1:9100，此时右侧显示“集群健康值: 未连接”
5. 关联header和elasticsearch
    修改elasticsearch解压目录config/elasticsearch.yml在末尾添加
    ```
        http.cors.enabled: true
        http.cors.allow-origin: "*"   
    ```
    重新启动elasticsearch和header  
    此时右侧显示“集群健康值: green (0 of 0)”,状态说明如下
    * green ES正常运行
    * yellow 集群的状态不是很好，但仍可以使用
    * red 集群状态很差，已经出现丢失数据的情况
    
三： 分布式集群环境搭建
1. 搭建主节点
    找到es的配置文件目录（config），追加一下几个配置
    ```
        # 集群的名称
        cluster.name: elasticsearch-cluster
        # 节点名称
        node.name: master
        # 是否为主节点
        node.master: true
        # 绑定ip
        network.host: 127.0.0.1
    ```
    启动header插件与es访问127.0.0.1:9100可以看到master主节点
2. 搭建一个从节点  
    i. 复制解压出来的es文件，自己任意命名，删除目录下的log和data文件  
    ii. 修改config目录下的配置文件，将第一步集群的配置覆盖为以下配置
    ```
        # 集群名称不变
        cluster.name: elasticsearch-cluster
        # 当前节点名称
        node.name: part1
        #　绑定节点ip
        network.host: 127.0.0.1
        # 是否为主节点
        node.master: false
        #　设置端口
        http.port: 9201
        #　告诉从机，主机在哪里，配置值为一个IP地址列表，存放master的IP地址
        discovery.zen.ping.unicast.hosts: ["127.0.0.1"]
    ```  
    iii. 进入从节点bin目录，新建run.bat，内容如下
    ```
        .\elasticsearch.bat -d
    ```
    iv. 进入127.0.0.1:9100可以看到一个master主节点与一个part1从节点
3. 重复第二步，修改下方两处
    ```
          # 当前节点名称
          node.name: part2
          #　设置端口
          http.port: 9202 
    ```
    再次启动，进入127.0.0.1:9100可以增加一个新的从节点part2

四： 基础理念  
参考网址 https://my.oschina.net/happyBKs/blog/1790876
1. 集群和节点  
    i. 一个集群由多个节点构成  
    ii. 在一个集群中，每个节点集群名称是一样的。在配置时，所有的节点都是通过一个唯一的集群名字来加入集群的  
    iii. 一个节点只可以隶属于一个集群  
    iv. 每个节点还有它自己的名字。如之前master、part1、part2  
    v. 节点可以存储数据，参与集群索引数据，以及搜索数据的独立服务  
2. 三大核心概念  
    i. 索引: 是含有相同属性的文档集合——作用类似数据库  
    ii. 类型： 一个索引里可以定义一个或者多个类型，通常我们会将有相同字段的文档作为一个类型——作用类似表
    iii. 文档：文档是可以被索引的基本数据单位。——作用类似表中的某一条记录  
3. 分片和备份  
- 分片：在ES中，每个索引都有多个分片，每个分片都是一个Lucene索引。  
- 备份：拷贝一个分片就完成的一个分片的备份。

五：创建索引
1. RESTFUL风格API  
    ES RESTFUL风格API的基本格式。  
    http://<ip>:<port>/<索引>/<类型>/<文档id>  
    API的URL里的每一个元素都是他的概念。即都是它的名词。  
    动词都是通过http方法来区分的（即发送方式不同）：
    GET/POST/PUT/DELETE
2. 创建索引——非结构化创建
    i. 启动第四部创建的一主两从节点，启动header插件，访问9100端口  
    ii. 依次点击【索引】——【新建索引】——输入索引名称（建议小写，不可以有双划线），使用默认分区，点击确定  
    iii. 再到概览里看一下。这里我们可以看到0、1、2、3、4分别指的是house索引的5个分片，它们分散存在master和两台part上  
        每个分片数字都有两个，因为我们刚才设置的副本数是1，每个分片中，有一个粗方框和一个细方框。粗方框代表这个分片是主分片，细方框代表分片的备份，即细方框是粗方框的备份分片  
    iv. 点击head插件页面上house索引的信息菜单按钮，选择【索引信息】，查看mappings属性。这个属性代表结构化信息，如果为空，代表次索引为非结构化索引。
3. 创建索引——结构化创建  
    i. 点击 【复合查询】，在POST左边的框中输入house/apartment/_mappings（为house创建一个apartment类型，_mappings代表这是一个映射）  
    ii. 点击【易读】复选框，输入json如下
    ```
        {
          "apartment": {
            "properties": {
              "apartmentname": {
                "type": "text"
              },
              "apartmentvalue": {
                "type": "text"
              }
            }
          }
        }
    ```
    这里代表apartment映射中有两个参数apartmentname、apartmentvalue类型都是text,点击【提交请求】  
    iii. 回到 【概览】点击右侧【刷新】，点击【信息】——【索引信息】会看到mappings如下  
    ```
        "mappings": {
            "apartment": {
                "properties": {
                    "apartmentname": {
                        "type": "text"
                    },
                    "apartmentvalue": {
                        "type": "text"
                    }
                }
            }
        },
    ```
    代表结构化创建创建成功
4. 使用postman创建索引  
    i. 打开postman选择PUT方式，路径使用http://localhost:9200/rent  
    ii. 选择body视图，选择raw，在右侧选择JSON类型，在下方输入以下内容
    ```
        {
        	"settings":{
        		"number_of_shards":3,
        		"number_of_replicas":1
        	},
        	"mappings":{
        		"community":{
        			"properties":{
        				"communityname":{
        					"type":"text"
        				},
        				"city":{
        					"type":"keyword"
        				},
        				"age":{
        					"type":"integer"
        				},
        				"creationdate":{
        					"type":"date",
        					"format":"yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis"
        				}
        			}
        		},
        		"shop":{
        			
        		}
        	}
        }
    ```
    不同类型说明如下  
    - text：  
    - keyword：存储数据时候，不会分词建立索引
    - integer：整型，小写
    - date：date类型的属性，可以指定format格式如果包含多种格式  
        可以将属性值中不同种类格式描述表达式用双竖线||分隔开  
        它还支持时间戳的格式，这里我们用epoch_mills来表示。
        
    如果返回以下信息则新建成功
    ```
        {
            "acknowledged": true,
            "shards_acknowledged": true,
            "index": "rent"
        }
    ```
    回到localhost:9100查看索引信息，显示已创建
    
六. 增加
1. 方式  
    i. 指定文档id插入 使用PUT方式发送  
    ii. 自动产生文档id插入 使用POST方式发送 
2. 指定文档id插入  
    使用post发送put请求，路径为  
    http://localhost:9200/rent/community/1
    这个路径表示信息为  
    IP:端口/index/type/id(IP:端口/索引/类型/id)  
    仍然选择body下的raw使用json发送以下属性信息
    ```
        {
        	"communityname":"海淀区3号四合院",
        	"city":"北京",
        	"age":10,
        	"creationdate":"2008-01-01"
        	
        }
    ```
    返回以下信息表示插入成功
    ```
        {
            "_index": "rent",
            "_type": "community",
            "_id": "1",
            "_version": 1,
            "result": "created",
            "_shards": {
                "total": 2,
                "successful": 2,
                "failed": 0
            },
            "created": true
        }
    ```
    返回127.0.0.1:9100 在对应的序列会看到docs:1(2)这表示这个文档共有一个，分为两份（备份一个）
3. 自动产生文档id插入   
    使用post发送post请求，路径为  
    http://localhost:9200/rent/community
    仍然选择body下的raw使用json发送以下属性信息  
    ```
    {
    	"communityname":"世茂滨江花园",
    	"city":"上海",
    	"age":9,
    	"creationdate":"2009-01-01 00:00:00"
    }
    ```
    返回以下信息表示插入成功
    ```
        {
            "_index": "rent",
            "_type": "community",
            "_id": "AWnh58kf-Em_WfDLXq2M",
            "_version": 1,
            "result": "created",
            "_shards": {
                "total": 2,
                "successful": 2,
                "failed": 0
            },
            "created": true
        }
    ```
    我们回到head插件刷新后看到：  
    rent索引的文档数量变成了2个，包含副本是4个
    
七. 修改
1. 直接修改文档  
打开postman，使用post协议，请求http://localhost:9200/rent/community/1/_update
选择body视图row发送json，内容如下
    ```
        {
            "doc":{
                "communityname":"海淀区三号四合院"
            }
        }
    ```
    返回如下表示更新成功
    ```
        {
            "_index": "rent",
            "_type": "community",
            "_id": "1",
            "_version": 2,
            "result": "updated",
            "_shards": {
                "total": 2,
                "successful": 2,
                "failed": 0
            }
        }
    ```
    total与successful为2表示两份信息都已经更新成功
2. 使用脚本的demo略过，有兴趣的可以去这个网址看一下  
    https://my.oschina.net/happyBKs/blog/1795682

八. 删除
1. 删除一个文档信息（删除某一条数据）  
打开postman，使用delete协议，访问http://localhost:9200/rent/community/1这个网址  
返回信息如下代表已删除
    ```
        {
            "found": true,
            "_index": "rent",
            "_type": "community",
            "_id": "1",
            "_version": 3,
            "result": "deleted",
            "_shards": {
                "total": 2,
                "successful": 2,
                "failed": 0
            }
        }
    ```
    访问header组件可以看到rent里id为1对应的数据已删除
2. 删除索引  
i. 使用api删除  
打开postman，使用delete协议，访问http://localhost:9200/house  
返回信息如下代表已删除
    ```
        {
            "acknowledged": true
        }
    ```
    回到header组件首页可以看到house已被删除
ii. 在header首页选择动作，删除，输入确认信息即可
至此rent与house已被删除

注意：使用集群化的ES是在太卡了，往下的案例都是只启动主节点

九.查询
1. 数据初始化准备
i. 创建index与type  
    打开postman使用put协议访问http://localhost:9200/rent
    选择body的row按钮，发送json请求，内容如下
    ```
        {
            "settings":{
                "number_of_shards":3,
                "number_of_replicas":0
            },
            "mappings":{
                "community":{
                    "properties":{
                        "communityname":{
                            "type":"text"
                        },
                        "city":{
                            "type":"keyword"
                        },
                        "age":{
                            "type":"integer"
                        },
                        "creationdate":{
                            "type":"date",
                            "format":"yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis"
                        }
                    }
                }
            }
        }
    ```
    这里就不设置备份了  
ii. 初始化数据
    使用postman发送post请求，路径为http://localhost:9200/rent/community/  
    选择body的row发送json数据，内容如下
    ```
        {
            "communityname":"东方明珠未来苑6期",
            "city":"上海",
            "age":5,
            "creationdate":"2008-01-01 00:00:00"
        }
    ```
    多做几条数据，在header插件中可以看到新增的数据
2. 查询
i. 简单查询
    使用postman发送get请求访问http://localhost:9200/rent/community/{id}  
    这个相当于根据id查询
ii. 查询所有
    使用postman发送post请求访问http://localhost:9200/rent/community/_search  
    在body的选择row发送json内容如下
    ```
        {
        	"query":{
        		"match_all":{}
        	}
        }
    ```
    这代表查询所有的信息，但是只会显示前10条  
    查询从何处到何处可使用以下方法
    ```
        {
        	"query":{
        		"match_all":{}
        	},
        	"from":0,
        	"size":12
        }
    ```
    这里代表查询从0到12条  
iii. 匹配查询  
    使用postman发送post请求访问http://localhost:9200/rent/community/_search  
    ```
        {
        	"query":{
        		"match":{
        			"communityname":"16期"
        		}
        	}
        }
    ```
    注意，此处如果doc的字段类型为text的话，会匹配所有的关键字，例如你好能匹配你们，好的等  
iv. 排序
    使用postman发送post请求访问http://localhost:9200/rent/community/_search  
    内容如下  
    ```
        {
        	"query":{
        		"match":{
        			"communityname":"16期"
        		}
        	},
        	"sort":[
        		{"creationdate":{"order":"desc"}}
        	]
        }
    ```
    此处是使用creationdate倒序显示  
v. 聚合查询   
    类似group by  
    使用postman发送post请求访问http://localhost:9200/rent/community/_search  
    ```
        {
        	"aggs":{
        		"my_group_by_age":{
        			"terms":{
        				"field":"age"
        			}
        		}
        	}
        }
    ```
    aggs是聚合查询的关键词，这里我想把所有的小区按照它们的房龄进行聚合。这里给我们的聚合条件取一个名字my_group_by_age。这个名字是自定义的，你可以随便起  
    我们使用terms关键词，指明我们需要按照某个字段进行聚合。指定字段需要用到filed关键词  
vi. 多聚合查询  
    使用postman发送post请求访问http://localhost:9200/rent/community/_search 
    ```
        {
            "aggs":{
                "my_group_by_age":{
                    "terms":{
                        "field":"age"
                    }
                },
                "my_group_by_creationdate":{
                    "terms":{
                        "field":"creationdate"
                    }
                }
            }
        }
    ```
    这里值得注意的是，建造时间creationdate是一个date类型，无论你在录入这条文档时用的是什么格式
    这里聚合是都是以这个date对应的时间戳数值作为聚合的key，不过结果里会在附加一个key_as_string把date对应的日期格式打印出来
    之前那几个没时分秒的日期也被转换成了时间戳，key_as_string显示为年月日时分秒的格式。  
vii. 其他聚合  
    stats 各类聚合统计计算各类聚合信息。包含count、max、min、avg等
    ```
        {
        	"aggs":{
        		"my_stats_age":{
        			"stats":{
        				"field":"age"
        			}
        		}
        	}
        }
    ```
    将stats换位min或其他可以只查询min的信息  
    
条件查询分类主要分为：子条件查询/复合条件查询

十. 子条件查询  
子条件查询主要分为
- Query context
- Filter Context
1. Query context  
Query context 常用的查询包括：  
全文本查询：针对文本类型的数据  
字段级别的查询：针对结构化的数据，比如数字、日期等  
i. 全文本查询  
    - 模糊匹配——match关键字
    ```
        {
        	"query":{
        		"match":{
        			"communityname":"中海万锦城(二期)"
        		}
        	}
        }
    ```
    - 习语匹配——match_phrase关键字
    ```
        {
        	"query":{
        		"match_phrase":{
        			"communityname":"中海万锦城(二期)"
        		}
        	}
        }
    ```
    - 多字段匹配——multi_match关键字
    ```
        {
        	"query":{
        		"multi_match":{
        			"query":"山江",
        			"fields":["communityname","city"]
        		}
        	}
        }
    ```
    可以看到小区名称或者地址信息中包含“山”或“江”的小区文档都被模糊匹配查询到了  
    query为查询的数值，fields为查询字段
    - 语法查询——query_string关键字
    ```
        {
        	"query":{
        		"query_string":{
        			"query":"期 AND 大道"
        		}
        	}
        }
    ```
    查询内容里包含‘期’和‘大道’的信息  
    此外也可以使用OR查询  
    - 多个字段的语法匹配——query_string关键字
    ```
    {
    	"query":{
    		"query_string":{
    			"query":"公园 OR 闵行",
    			"fields":["communityname","city"]
    		}
    	}
    }
    ```  
    此处使用了query_String的复合查询   
    查询communityname和city中包含公园或闵行的信息  
    - 字段级别的查询
    ```
    {
    	"query":{
    		"term":{
    			"age":10
    		}
    	}
    }
    ```
   注意  
   term查询时text文本类型不适用于字段级别的查询，无论怎么查询都不会查出数据，适用于keyword的字段
   - 范围查询——range
   ```
   {
   	"query":{
   		"range":{
   			"age": {
   				"gte":5,
   				"lt":7
   			}
   		}
   	}
   }
   ``` 
    | 关键词      | 含义     |  对应数字符号|
    | --------   | -----:  | :----: |
    | gt         | 大于     |   >    |
    | gte        | 大于等于  |   >=    |
    | lt         | 小于     |   <    |
    | lte        | 小于等于  |   <=    |
    日期范围查询  
    ```
        {
        	"query":{
        		"range":{
        			"creationdate": {
        				"gte":"2011-12-31",
        				"lt":"2014-01-01"
        			}
        		}
        	}
        }
    ```
    其中，时间也可以用now代表当前时间
    ```
        {
        	"query":{
        		"range":{
        			"creationdate": {
        				"gte":"2015-01-01",
        				"lt":"now"
        			}
        		}
        	}
        }
    ```
2. Filter  
    详见：https://my.oschina.net/happyBKs/blog/1799392    