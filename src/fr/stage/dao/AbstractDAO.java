/**
 * 
 */
package fr.stage.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.stage.utils.Introspection;

/**
 * @author rnonnon
 * 
 */
public abstract class AbstractDAO<T> implements ICRUDManager<T> {

    protected IConnectionManager connectionManager = ConnectionManager
	    .getInstance();

    protected ResultSet res;

    protected Statement stm;

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    protected Connection beforeOperation() {
	Connection connection = connectionManager.getConnection();
	return connection;
    }

    protected void afterOperation() {
	connectionManager.closeConnection();
	Introspection.closeSafe(res);
	Introspection.closeSafe(stm);
    }

    @Override
    public void create(T object) {
	logger.info("Start create {}", object);
	Connection connection = beforeOperation();
	createBody(object, connection);
	afterOperation();
	logger.info("End Create {}", object);
    }

    @Override
    public void find(final T object) {
	Connection connection = beforeOperation();
	findBody(object, connection);
	afterOperation();
    }

    @Override
    public void update(final T object) {
	logger.info("Start update {}", object);
	Connection connection = beforeOperation();
	updateBody(object, connection);
	afterOperation();
	logger.info("End update {}", object);
    }

    @Override
    public void delete(final T object) {
	logger.info("Start delete {}", object);
	Connection connection = beforeOperation();
	deleteBody(object, connection);
	afterOperation();
	logger.info("End delete {}", object);
    }

    protected abstract void createBody(T object, Connection connection);

    protected abstract void findBody(T object, Connection connection);

    protected abstract void updateBody(T object, Connection connection);

    protected abstract void deleteBody(T object, Connection connection);

    public <idType> String genericFindQuery(String className, idType id) {
	StringBuffer query = new StringBuffer();
	query.append("SELECT * FROM ");
	query.append(className);
	query.append(" WHERE id = ");
	query.append(id);
	return query.toString();
    }

    // public String genericInsertQuery(final T object){
    // StringBuffer query = new StringBuffer();
    // query.append("INSERT INTO ");
    // query.append(object.getClass().getName());
    // query.append(" ( ");
    // final Field[] fields = object.getClass().getDeclaredFields();
    // for(int i=0;i<fields.length;++i){
    // query.append()
    //
    // }
    // query.append(" ) ");
    // return "";
    // }
}