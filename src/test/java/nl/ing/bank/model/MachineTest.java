package nl.ing.bank.model;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class MachineTest {

  private Machine machine;

  @Before
  public void setUp() throws Exception {
    machine = new Machine('M', 'C', 'R');
  }

  @Test
  public void should_initialise_machine_with_alphabetical_characters() throws Exception {
    assertThat(machine, is(notNullValue()));
  }

  @Test(expected = IllegalArgumentException.class)
  public void should_not_initialise_machine_with_non_alphabetical_characters() throws Exception {
    assertNotNull(new Machine('1','3','#'));
  }

  @Test
  public void should_correctly_get_input() {
    final Map<Long, Character> reflector = machine.getReflector();
    assertThat(reflector.size(), equalTo(26));
    assertThat(reflector.get(10L), equalTo('K'));
  }

  @Test
  public void should_correctly_get_reflector() {
    final Map<Long, Character> input = machine.getInput();
    assertThat(input.size(), equalTo(26));
    assertThat(input.get(14L), equalTo('O'));
  }
}