package com.sheaney;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

public class App {
    private static final String ADDRESS = "127.0.0.1";
    private static final int NUMBER_OF_RECORDS = 10;

    private static final Random RANDOM = new Random();

    public static void main( String[] args ) {
        try (
            Cluster cluster = Cluster.builder().addContactPoint(ADDRESS).withoutJMXReporting().build();
            Session session = cluster.connect()
        ) {

            // Create schema
            session.execute("CREATE KEYSPACE IF NOT EXISTS devprod\n" +
                    "  WITH REPLICATION = {\n" +
                    "    'class': 'SimpleStrategy',\n" +
                    "    'replication_factor': 1\n" +
                    "  }");

            session.execute("CREATE TABLE IF NOT EXISTS devprod.tsv1 (\n" +
                    "  id uuid PRIMARY KEY,\n" +
                    "  test_set set<int>,\n" +
                    ")");

            // Insert some records
            MappingManager manager = new MappingManager(session);
            Mapper<tsv1> mapper = manager.mapper(tsv1.class);
            mapper.setDefaultSaveOptions(Mapper.Option.saveNullFields(false));

            for (int i = 0; i < NUMBER_OF_RECORDS; i++) {
                mapper.save(random_tstestv1());
            }
        }
    }

    private static tsv1 random_tstestv1() {
        tsv1 table = new tsv1();

        table.setId(UUID.randomUUID());

        Set<Integer> test_set = new HashSet<>();
        int N = RANDOM.nextInt(3) + 1;
        for (int i = 0; i < N; i++) {
            test_set.add(RANDOM.nextInt());
        }

        table.setTest_set(test_set);

        return table;
    }
}
