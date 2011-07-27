package no.advide

import java.awt.BorderLayout as BL

import groovy.swing.SwingBuilder
import javax.swing.BorderFactory
import no.advide.ui.Cursor
import no.advide.ui.EditorPanel
import no.advide.ui.TextLayout
import no.advide.ui.KeyInterpreter

class Application {
  static main(args) {
    def editorPanel = new EditorPanel()

    editorPanel.textLayout = new TextLayout(lines: ["Hello", "World"], cursor: new Cursor(x: 0, y: 0))

    new KeyInterpreter(editorPanel).addListener(new PrintListener())

    new SwingBuilder().edt {
      frame(title: 'Frame', size: [810, 600], show: true) {
        borderLayout()
        panel(constraints: BL.CENTER, border: BorderFactory.createEmptyBorder(3, 6, 3, 6)) {
          borderLayout()
          panel(editorPanel, constraints: BL.CENTER, focusable: true)
        }
      }
    }
  }

  static class PrintListener {
    void actionTyped(k) {
      println "Action: ${k}"
    }

    void charTyped(c) {
      println "Char: ${c}"
    }
  }
}
