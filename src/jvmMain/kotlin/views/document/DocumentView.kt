package views.document

import actions.DocumentListener

interface DocumentView {
    fun addDocumentListener(documentListener: DocumentListener)
    fun removeDocumentListener(documentListener: DocumentListener)
}