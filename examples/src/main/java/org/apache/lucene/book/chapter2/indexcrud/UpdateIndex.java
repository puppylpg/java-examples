package org.apache.lucene.book.chapter2.indexcrud;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.book.chapter2.ik.IKAnalyzer6x;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class UpdateIndex {
    public static void main(String[] args) {
        Analyzer analyzer = new IKAnalyzer6x();
        IndexWriterConfig icw = new IndexWriterConfig(analyzer);
        Path indexPath = Paths.get(System.getProperty("java.io.tmpdir") + "/lucene");
        Directory directory;
        try {
            directory = FSDirectory.open(indexPath);
            IndexWriter indexWriter = new IndexWriter(directory, icw);
            Document doc = new Document();
            doc.add(new TextField("id", "2", Store.YES));
            doc.add(new TextField("title", " 北京大学开学迎来4380名新生", Store.YES));
            doc.add(new TextField("content", " 昨天，北京大学迎来4380名来自全国各地及数十个国家的本科新生。其中，农村学生共700余名，为近年最多...", Store.YES));
            indexWriter.updateDocument(new Term("title", "北大"), doc);
            indexWriter.commit();
            indexWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
