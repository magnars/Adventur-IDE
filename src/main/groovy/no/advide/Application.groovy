package no.advide

import java.awt.BorderLayout as BL

import groovy.swing.SwingBuilder
import javax.swing.BorderFactory
import no.advide.ui.EditorPanel
import no.advide.ui.KeyInterpreter

class Application {
  static main(args) {
    def editorPanel = new EditorPanel()
    def editor = new Editor(lines: ["Hello", "World"], cursor: new Cursor(x: 0, y: 0))

    editorPanel.textLayout = editor.getTextLayout()

    editor.onChange { textLayout ->
      editorPanel.textLayout = textLayout
      editorPanel.repaint()
    }

    new KeyInterpreter(editorPanel).addListener(editor)

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

}
