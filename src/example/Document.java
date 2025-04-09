package example;

import db.*;
import java.util.Date;

public class Document extends Entity implements Trackable {
    public String content;
    private Date creationDate;
    private Date lastModificationDate;

    public Document(String content) {
        this.content = content;
    }

    @Override
    public Entity copy() {
        Document copyDocument = new Document(content);
        copyDocument.id = id;
        copyDocument.creationDate = new Date(creationDate.getTime());
        copyDocument.lastModificationDate = new Date(lastModificationDate.getTime());

        return copyDocument;
    }

    @Override
    public int getEntityCode() {
        return 0;
    }

    @Override
    public void setCreationDate(Date date) {
        this.creationDate = new Date(date.getTime());
    }

    @Override
    public Date getCreationDate() {
        return new Date(creationDate.getTime());
    }

    @Override
    public void setLastModificationDate(Date date) {
        this.lastModificationDate = new Date(date.getTime());
    }

    @Override
    public Date getLastModificationDate() {
        return new Date(lastModificationDate.getTime());
    }
}
