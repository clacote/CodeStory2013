package com.ninja_squad.codestory.scalaskel;

import com.google.common.collect.ImmutableMap;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class ChangeComputerTest {

    private ChangeComputer computer = ChangeComputer.getInstance();

    @Test
    public void change1() throws Exception {
        assertThat(computer.change(1)).containsOnly(ImmutableMap.of(Unite.foo, 1));
    }

    @Test
    public void change7() throws Exception {
        assertThat(computer.change(7)).containsOnly(
                ImmutableMap.of(Unite.foo, 7),
                ImmutableMap.of(Unite.bar, 1)
        );
    }
}
