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

    @Test
    public void change19() throws Exception {
        assertThat(computer.change(19)).containsOnly(
                ImmutableMap.of(Unite.foo, 19),
                ImmutableMap.of(Unite.foo, 12, Unite.bar, 1),
                ImmutableMap.of(Unite.foo, 5, Unite.bar, 2),
                ImmutableMap.of(Unite.foo, 8, Unite.qix, 1),
                ImmutableMap.of(Unite.foo, 1, Unite.bar, 1, Unite.qix, 1)
        );
    }

    @Test
    public void change97() throws Exception {
        assertThat(computer.change(97)).contains(ImmutableMap.of(Unite.foo, 97));
        assertThat(computer.change(97)).hasSize(162);
    }
}
