package no.advide.ui

import java.awt.event.KeyEvent
import javax.swing.JComponent

class KeyInterpreter {
  def listeners = []
  def actionCallbacks = [:]

  void addListener(l) {
    listeners << l
  }

  void onAction(action, callback) {
    if (!actionCallbacks[action]) actionCallbacks[action] = []
    actionCallbacks[action] << callback
  }

  KeyInterpreter(JComponent component) {
    component.keyPressed = { KeyEvent e -> handleKeyPress(e) }
  }

  def handleKeyPress(KeyEvent e) {
    if (modifier(e)) return notifyAction(modifier(e))
    if (specialChar(e)) return notifyChar(specialChar(e))
    if (!isActionPress(e)) return notifyChar(e.keyChar)
    def keys = currentModifiers(e) + pressedKey(e)
    notifyAction(keys.join("+"))
  }

  def pressedKey(KeyEvent e) {
    return actionKey(e) ? actionKey(e) : KeyEvent.getKeyText(e.keyCode)
  }

  def isActionPress(KeyEvent e) {
    return e.isControlDown() || e.isAltDown() || e.isMetaDown() || e.isActionKey() || actionKey(e)
  }

  def notifyChar(c) {
    listeners.each { it.charTyped c }
  }

  def notifyAction(a) {
    listeners.each { it.actionTyped a }
    if (actionCallbacks[a])
      actionCallbacks[a].each { it.call() }
  }

  def modifier(KeyEvent e) {
    return e.keyCode in [16, 17, 18, 157] ? currentModifiers(e).join("+") : null
  }

  def currentModifiers(KeyEvent e) {
    def keys = []
    if (e.isControlDown()) keys << "ctrl"
    if (e.isShiftDown()) keys << "shift"
    if (e.isAltDown()) keys << "alt"
    if (e.isMetaDown()) keys << "cmd"
    return keys
  }

  def actionKey(KeyEvent e) {
    switch (e.keyCode) {
      case 8: return "backspace"
      case 9: return "tab"
      case 10: return "enter"
      case 37: return "left"
      case 40: return "down"
      case 39: return "right"
      case 38: return "up"
      case 27: return "escape"
    }
  }

  def specialChar(KeyEvent e) {
    if (e.isAltDown() && e.keyCode == 55) return e.isShiftDown() ? "\\" : "|"
    if (e.isAltDown() && e.keyCode == 56) return e.isShiftDown() ? "{" : "["
    if (e.isAltDown() && e.keyCode == 57) return e.isShiftDown() ? "}" : "]"
    switch (e.keyCode) {
      case 59: return e.isShiftDown() ? "Ø" : "ø"
      case 222: return e.isShiftDown() ? "Æ" : "æ"
      case 91: return e.isShiftDown() ? "Å" : "å"
    }
  }
}
