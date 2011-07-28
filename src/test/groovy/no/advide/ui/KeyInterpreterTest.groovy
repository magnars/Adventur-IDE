package no.advide.ui

import java.awt.event.KeyEvent
import javax.swing.JPanel

class KeyInterpreterTest extends GroovyTestCase {

  def listener
  def interpreter

  void setUp() {
    listener = new MockKeyListener()
    interpreter = new KeyInterpreter(new JPanel())
    interpreter.addListener(listener)
  }

  void test_normal_key() {
    assertKeyRecevied "Char: T", new MockKeyEvent(keyChar: "T")
  }

  void test_norwegian_key() {
    assertKeyRecevied "Char: Ø", new MockKeyEvent(keyCode: 59, shift: true)
  }

  void test_curly_braces() {
    assertKeyRecevied "Char: {", new MockKeyEvent(keyCode: 56, alt: true, shift: true)
  }

  void test_action_key() {
    assertKeyRecevied "Action: enter", new MockKeyEvent(keyCode: 10)
  }

  void test_action_with_modifier() {
    assertKeyRecevied "Action: ctrl+tab", new MockKeyEvent(keyCode: 9, ctrl: true)
  }

  void test_pure_modifier() {
    assertKeyRecevied "Action: shift", new MockKeyEvent(keyCode: 16, shift: true)
  }

  void test_modified_modifier() {
    assertKeyRecevied "Action: ctrl+shift", new MockKeyEvent(keyCode: 16, shift: true, ctrl: true)
  }

  void test_modified_char() {
    assertKeyRecevied "Action: ctrl+shift+alt+cmd+D", new MockKeyEvent(keyCode: 68, ctrl: true, cmd: true, alt: true, shift: true)
  }

  void assertKeyRecevied(recevied, event) {
    interpreter.handleKeyPress(event)
    assert listener.received == recevied
  }

  static class MockKeyListener {
    def received
    void actionTyped(k) { received = "Action: ${k}" }
    void charTyped(c)   { received = "Char: ${c}" }
  }

  static class MockKeyEvent extends KeyEvent {
    MockKeyEvent() { super(new JPanel(), 0, 0L, 0, 0, " ".charAt(0)) }

    def keyCode, keyChar, alt, shift, ctrl, cmd, action
    boolean isAltDown()   { alt }
    boolean isShiftDown() { shift }
    boolean isControlDown()  { ctrl }
    boolean isMetaDown()   { cmd }
    boolean isActionKey()   { action }
  }

}
