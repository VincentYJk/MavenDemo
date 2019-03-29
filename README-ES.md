[参考网站]https://my.oschina.net/happyBKs/blog/1790773  
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
代表结构化创建创建成果