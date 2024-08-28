package io.codety.scanner.analyzer.checkov.dto;

import java.util.ArrayList;

public class CheckovFailedCheck {
    private String check_id;
    private String bc_check_id;
    private String check_name;
    private CheckovCheckResult check_result;
    private ArrayList<ArrayList<Object>> code_block;
    private String file_path;
    private String file_abs_path;
    private String repo_file_path;
    private ArrayList<Integer> file_line_range;
    private String resource;
    private Object evaluations;
    private String check_class;
    private Object fixed_definition;
    private CheckovEntityTags entity_tags;
    private Object caller_file_path;
    private Object caller_file_line_range;
    private Object resource_address;
    private Object severity;
    private Object bc_category;
    private Object benchmarks;
    private Object description;
    private Object short_description;
    private Object vulnerability_details;
    private Object connected_node;
    private String guideline;
    private ArrayList<Object> details;
    private Object check_len;
    private String definition_context_file_path;
//    private Breadcrumbs breadcrumbs;
    private String validation_status;
    private String added_commit_hash;
    private String removed_commit_hash;
    private String added_by;
    private String removed_date;
    private String added_date;

    public String getCheck_id() {
        return check_id;
    }

    public void setCheck_id(String check_id) {
        this.check_id = check_id;
    }

    public String getBc_check_id() {
        return bc_check_id;
    }

    public void setBc_check_id(String bc_check_id) {
        this.bc_check_id = bc_check_id;
    }

    public String getCheck_name() {
        return check_name;
    }

    public void setCheck_name(String check_name) {
        this.check_name = check_name;
    }

    public CheckovCheckResult getCheck_result() {
        return check_result;
    }

    public void setCheck_result(CheckovCheckResult check_result) {
        this.check_result = check_result;
    }

    public ArrayList<ArrayList<Object>> getCode_block() {
        return code_block;
    }

    public void setCode_block(ArrayList<ArrayList<Object>> code_block) {
        this.code_block = code_block;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

    public String getFile_abs_path() {
        return file_abs_path;
    }

    public void setFile_abs_path(String file_abs_path) {
        this.file_abs_path = file_abs_path;
    }

    public String getRepo_file_path() {
        return repo_file_path;
    }

    public void setRepo_file_path(String repo_file_path) {
        this.repo_file_path = repo_file_path;
    }

    public ArrayList<Integer> getFile_line_range() {
        return file_line_range;
    }

    public void setFile_line_range(ArrayList<Integer> file_line_range) {
        this.file_line_range = file_line_range;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public Object getEvaluations() {
        return evaluations;
    }

    public void setEvaluations(Object evaluations) {
        this.evaluations = evaluations;
    }

    public String getCheck_class() {
        return check_class;
    }

    public void setCheck_class(String check_class) {
        this.check_class = check_class;
    }

    public Object getFixed_definition() {
        return fixed_definition;
    }

    public void setFixed_definition(Object fixed_definition) {
        this.fixed_definition = fixed_definition;
    }

    public CheckovEntityTags getEntity_tags() {
        return entity_tags;
    }

    public void setEntity_tags(CheckovEntityTags entity_tags) {
        this.entity_tags = entity_tags;
    }

    public Object getCaller_file_path() {
        return caller_file_path;
    }

    public void setCaller_file_path(Object caller_file_path) {
        this.caller_file_path = caller_file_path;
    }

    public Object getCaller_file_line_range() {
        return caller_file_line_range;
    }

    public void setCaller_file_line_range(Object caller_file_line_range) {
        this.caller_file_line_range = caller_file_line_range;
    }

    public Object getResource_address() {
        return resource_address;
    }

    public void setResource_address(Object resource_address) {
        this.resource_address = resource_address;
    }

    public Object getSeverity() {
        return severity;
    }

    public void setSeverity(Object severity) {
        this.severity = severity;
    }

    public Object getBc_category() {
        return bc_category;
    }

    public void setBc_category(Object bc_category) {
        this.bc_category = bc_category;
    }

    public Object getBenchmarks() {
        return benchmarks;
    }

    public void setBenchmarks(Object benchmarks) {
        this.benchmarks = benchmarks;
    }

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public Object getShort_description() {
        return short_description;
    }

    public void setShort_description(Object short_description) {
        this.short_description = short_description;
    }

    public Object getVulnerability_details() {
        return vulnerability_details;
    }

    public void setVulnerability_details(Object vulnerability_details) {
        this.vulnerability_details = vulnerability_details;
    }

    public Object getConnected_node() {
        return connected_node;
    }

    public void setConnected_node(Object connected_node) {
        this.connected_node = connected_node;
    }

    public String getGuideline() {
        return guideline;
    }

    public void setGuideline(String guideline) {
        this.guideline = guideline;
    }

    public ArrayList<Object> getDetails() {
        return details;
    }

    public void setDetails(ArrayList<Object> details) {
        this.details = details;
    }

    public Object getCheck_len() {
        return check_len;
    }

    public void setCheck_len(Object check_len) {
        this.check_len = check_len;
    }

    public String getDefinition_context_file_path() {
        return definition_context_file_path;
    }

    public void setDefinition_context_file_path(String definition_context_file_path) {
        this.definition_context_file_path = definition_context_file_path;
    }


    public String getValidation_status() {
        return validation_status;
    }

    public void setValidation_status(String validation_status) {
        this.validation_status = validation_status;
    }

    public String getAdded_commit_hash() {
        return added_commit_hash;
    }

    public void setAdded_commit_hash(String added_commit_hash) {
        this.added_commit_hash = added_commit_hash;
    }

    public String getRemoved_commit_hash() {
        return removed_commit_hash;
    }

    public void setRemoved_commit_hash(String removed_commit_hash) {
        this.removed_commit_hash = removed_commit_hash;
    }

    public String getAdded_by() {
        return added_by;
    }

    public void setAdded_by(String added_by) {
        this.added_by = added_by;
    }

    public String getRemoved_date() {
        return removed_date;
    }

    public void setRemoved_date(String removed_date) {
        this.removed_date = removed_date;
    }

    public String getAdded_date() {
        return added_date;
    }

    public void setAdded_date(String added_date) {
        this.added_date = added_date;
    }
}