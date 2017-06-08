package kr.co.ioacademy.base;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;

/**
 * sqlSession 관리용
 * @author Administrator
 *
 */
abstract public class BaseDAO extends SqlSessionDaoSupport {
	protected final Log logger = LogFactory.getLog(this.getClass());
}
