package com.graphql.tutorial.salesservice.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "customer_documents")
public class CustomerDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String documentType;

    private String documentPath;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getDocumentPath() {
        return documentPath;
    }

    public void setDocumentPath(String documentPath) {
        this.documentPath = documentPath;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CustomerDocument{");
        sb.append("id=").append(id);
        sb.append(", documentType='").append(documentType).append('\'');
        sb.append(", documentPath='").append(documentPath).append('\'');
        sb.append('}');
        return sb.toString();
    }


    public static final class CustomerDocumentBuilder {
        private Long id;
        private String documentType;
        private String documentPath;

        private CustomerDocumentBuilder() {
        }

        public static CustomerDocumentBuilder aCustomerDocument() {
            return new CustomerDocumentBuilder();
        }

        public CustomerDocumentBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public CustomerDocumentBuilder documentType(String documentType) {
            this.documentType = documentType;
            return this;
        }

        public CustomerDocumentBuilder documentPath(String documentPath) {
            this.documentPath = documentPath;
            return this;
        }

        public CustomerDocument build() {
            CustomerDocument customerDocument = new CustomerDocument();
            customerDocument.setId(id);
            customerDocument.setDocumentType(documentType);
            customerDocument.setDocumentPath(documentPath);
            return customerDocument;
        }
    }
}
