package com.sheaney;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

import java.util.Set;
import java.util.UUID;

@Table(keyspace = "devprod", name = "tsv1")
public class tsv1 {
    @PartitionKey
    @Column(name = "id")
    UUID id;

    @Column(name = "test_set")
    Set<Integer> test_set;

    public tsv1() {}

    public void setId(UUID id) {
        this.id = id;
    }

    public void setTest_set(Set<Integer> test_set) {
        this.test_set = test_set;
    }
}
