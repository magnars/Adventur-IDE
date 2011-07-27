package no.advide.ui

import java.awt.event.KeyEvent
import javax.swing.JComponent

class KeyInterpreter {
  def listeners = []

  void addListener(l) {
    listeners << l
  }

  KeyInterpreter(JComponent component) {
    component.keyPressed = { KeyEvent e ->
      if (modifier(e)) return notifyAction(modifier(e))
      if (specialChar(e)) return notifyChar(specialChar(e))
      if (!isActionPress(e)) return notifyChar(e.keyChar)
      def keys = []
      if (e.isControlDown() || e.isMetaDown()) keys << "ctrl"
      if (e.isShiftDown()) keys << "shift"
      if (e.isAltDown()) keys << "alt"
      if (actionKey(e)) keys << actionKey(e)
      else keys << KeyEvent.getKeyText(e.keyCode)
      notifyAction(keys.join("+"))
    }
  }

  def isActionPress(KeyEvent e) {
    return e.isControlDown() || e.isAltDown() || e.isMetaDown() || e.isActionKey() || actionKey(e)
  }

  def notifyChar(c) {
    listeners.each { l -> l.charTyped c }
  }

  def notifyAction(a) {
    listeners.each { l -> l.actionTyped a }
  }

  def modifier(KeyEvent e) {
    switch (e.keyCode) {
      case 16: return "shift"
      case 17: return "ctrl"
      case 18: return "alt"
      case 157: return "ctrl"
    }
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
