package vn.iotstar.configs;

import jakarta.persistence.*;
@PersistenceContext
public class JPAConfig {
	public static EntityManager getEntityManager() {
		 EntityManagerFactory factory = Persistence.createEntityManagerFactory("dataSource");
		 return factory.createEntityManager();
		 }

}
