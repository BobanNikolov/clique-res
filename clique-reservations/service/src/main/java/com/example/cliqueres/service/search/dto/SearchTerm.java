package com.example.cliqueres.service.search.dto;

// this monster annotation is required, so that jackson can deserialize the polymorphic QueryTerm list. Then it decides,
// based on the properties of the json object string, which of the subtypes should be used for deserialization.
public interface SearchTerm {

}
