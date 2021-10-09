package com.ebook.ebook.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="meta",schema = "e-book",uniqueConstraints = @UniqueConstraint(columnNames = {"meta_key"}))
@JsonIgnoreProperties(value={"handler","hibernateLazyInitializer","filedHandler"})
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "metaId"
)

public class Meta {

    @Id
    @Column(name="meta_id")
    private Integer metaId;

    @Basic
    @Column(name="meta_key")
    private String metaKey;

    @Basic
    @Column(name="meta_value")
    private Integer metaValue;

    public Integer getMetaId() {
        return metaId;
    }

    public void setMetaId(Integer metaId) {
        this.metaId = metaId;
    }

    public String getMetaKey() {
        return metaKey;
    }

    public void setMetaName(String metaName) {
        this.metaKey = metaName;
    }

    public Integer getMetaValue() {
        return metaValue;
    }

    public void setMetaValue(Integer metaValue) {
        this.metaValue = metaValue;
    }

    public Meta() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Meta meta = (Meta) o;
        return metaId.equals(meta.metaId) && metaKey.equals(meta.metaKey) && metaValue.equals(meta.metaValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(metaId, metaKey, metaValue);
    }
}
