package it.denv.languagetool.languageserver;

import org.eclipse.lsp4j.*;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.lsp4j.services.TextDocumentService;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * `TextDocumentService` that only supports `TextDocumentSyncKind.Full` updates.
 * Override members to add functionality.
 */
class FullTextDocumentService implements TextDocumentService {

    @NotNull
    HashMap<String, TextDocumentItem> documents;

    public FullTextDocumentService(HashMap<String, TextDocumentItem> documents) {
        this.documents = documents;
    }

    public FullTextDocumentService() {
        this.documents = new HashMap<>();
    }

    @Override
    public CompletableFuture<CompletionItem> resolveCompletionItem(CompletionItem unresolved) {
        return null;
    }

    @Override
    public CompletableFuture<Hover> hover(TextDocumentPositionParams position) {
        return null;
    }

    @Override
    public CompletableFuture<SignatureHelp> signatureHelp(TextDocumentPositionParams position) {
        return null;
    }

    @Override
    public CompletableFuture<Either<List<? extends Location>, List<? extends LocationLink>>> definition(TextDocumentPositionParams position) {
        return null;
    }

    @Override
    public CompletableFuture<List<? extends Location>> references(ReferenceParams params) {
        return null;
    }

    @Override
    public CompletableFuture<List<? extends DocumentHighlight>> documentHighlight(TextDocumentPositionParams position) {
        return null;
    }

    @Override
    public CompletableFuture<List<? extends CodeLens>> codeLens(CodeLensParams params) {
        return null;
    }

    @Override
    public CompletableFuture<CodeLens> resolveCodeLens(CodeLens unresolved) {
        return null;
    }

    @Override
    public CompletableFuture<List<? extends TextEdit>> formatting(DocumentFormattingParams params) {
        return null;
    }

    @Override
    public CompletableFuture<List<? extends TextEdit>> rangeFormatting(DocumentRangeFormattingParams params) {
        return null;
    }

    @Override
    public CompletableFuture<List<? extends TextEdit>> onTypeFormatting(DocumentOnTypeFormattingParams params) {
        return null;
    }

    @Override
    public CompletableFuture<WorkspaceEdit> rename(RenameParams params) {
        return null;
    }

    @Override
    public void didOpen(DidOpenTextDocumentParams params) {
        documents.put(params.getTextDocument().getUri(), params.getTextDocument());
    }

    @Override
    public void didChange(DidChangeTextDocumentParams params) {
        String uri = params.getTextDocument().getUri();
        for (TextDocumentContentChangeEvent changeEvent : params.getContentChanges()) {
            // Will be full update because we specified that is all we support
            if (changeEvent.getRange() != null) {
                throw new UnsupportedOperationException("Range should be null for full document update.");
            }
            if (changeEvent.getRangeLength() != null) {
                throw new UnsupportedOperationException("RangeLength should be null for full document update.");
            }

            documents.get(uri).setText(changeEvent.getText());
            documents.get(uri).setVersion(params.getTextDocument().getVersion());
        }
    }

    @Override
    public void didClose(DidCloseTextDocumentParams params) {
        String uri = params.getTextDocument().getUri();
        documents.remove(uri);
    }

    @Override
    public void didSave(DidSaveTextDocumentParams params) {
    }
}
