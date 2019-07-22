package demo.dao.config.system;

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
 * redisSession操作dao
 *
 * @author 梁明辉
 */
@Component
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
        if (session == null || session.getId() == null) {
            return;
        }
        session.setTimeout(expireTime);
        redisTemplate.opsForValue().set(session.getId(), session, expireTime, TimeUnit.MILLISECONDS);
    }

    /**
     * 删除session
     *
     * @param session 对应获取到的session
     */
    @Override
    public void delete(Session session) {
        if (session == null) {
            return;
        }
        redisTemplate.opsForValue().getOperations().delete(session.getId());
    }

    /**
     * 获取活跃的session，可以用来统计在线人数，如果要实现这个功能，可以在将session加入redis时指定一个session前缀，统计的时候则使用keys("session-prefix*")的方式来模糊查找redis中所有的session集合
     *
     * @return session集合
     */
    @Override
    public Collection<Session> getActiveSessions() {
        return redisTemplate.keys("*");
    }

    /**
     * 加入session
     *
     * @param session 对应获取到的session
     * @return 序列化的session
     */
    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = this.generateSessionId(session);
        this.assignSessionId(session, sessionId);

        redisTemplate.opsForValue().set(session.getId(), session, expireTime, TimeUnit.MILLISECONDS);
        return sessionId;
    }

    /**
     * 读取session
     *
     * @param sessionId 对应的session
     * @return 返回读取到的session
     */
    @Override
    public Session doReadSession(Serializable sessionId) {
        if (sessionId == null) {
            return null;
        }
        return (Session) redisTemplate.opsForValue().get(sessionId);
    }

}
