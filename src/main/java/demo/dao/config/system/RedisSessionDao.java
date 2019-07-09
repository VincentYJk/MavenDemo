package demo.dao.config.system;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName RedisSessionDao
 * @Description redisSession操作dao
 * @Author 梁明辉
 * @Date 2019/7/2 13:58
 * @ModifyDate 2019/7/2 13:58
 * @Version 1.0
 */
@Component
@Slf4j
public class RedisSessionDao extends AbstractSessionDAO {

    /**
     * session有效期单位是毫秒
     */
    private long expireTime = 3 * 60 * 1000;
    @Autowired
    private RedisTemplate redisTemplate;

    public RedisSessionDao() {
        super();
    }

    public RedisSessionDao(long expireTime, RedisTemplate redisTemplate) {
        super();
        this.expireTime = expireTime;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        log.info("====update======");
        if (session == null || session.getId() == null) {
            return;
        }
        session.setTimeout(expireTime);
        redisTemplate.opsForValue().set(session.getId(), session, expireTime, TimeUnit.MILLISECONDS);
    }

    /**
     * @Description 删除session
     * @Author 梁明辉
     * @Date 11:17 2019-07-09
     * @ModifyDate 11:17 2019-07-09
     * @Params [session]
     * @Return void
     */
    @Override
    public void delete(Session session) {
        log.info("=====delete=====");
        if (session == null) {
            return;
        }
        redisTemplate.opsForValue().getOperations().delete(session.getId());
    }

    /**
     * @Description // 获取活跃的session，可以用来统计在线人数，如果要实现这个功能，可以在将session加入redis时指定一个session前缀，统计的时候则使用keys("session-prefix*")的方式来模糊查找redis中所有的session集合
     * @Author 梁明辉
     * @Date 11:33 2019-07-09
     * @ModifyDate 11:33 2019-07-09
     * @Params []
     * @Return java.util.Collection<org.apache.shiro.session.Session>
     */
    @Override
    public Collection<Session> getActiveSessions() {
        log.info("====getActiveSessions======");
        return redisTemplate.keys("*");
    }

    /**
     * @Description 加入session
     * @Author 梁明辉
     * @Date 11:37 2019-07-09
     * @ModifyDate 11:37 2019-07-09
     * @Params [session]
     * @Return java.io.Serializable
     */
    @Override
    protected Serializable doCreate(Session session) {
        log.info("===============doCreate================");
        Serializable sessionId = this.generateSessionId(session);
        this.assignSessionId(session, sessionId);

        redisTemplate.opsForValue().set(session.getId(), session, expireTime, TimeUnit.MILLISECONDS);
        return sessionId;
    }

    /**
     * @Description 读取session
     * @Author 梁明辉
     * @Date 11:34 2019-07-09
     * @ModifyDate 11:34 2019-07-09
     * @Params
     * @Return
     */
    @Override
    public Session doReadSession(Serializable sessionId) {
        log.info("=====doReadSession=====");
        if (sessionId == null) {
            return null;
        }
        return (Session) redisTemplate.opsForValue().get(sessionId);
    }

}
