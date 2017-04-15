<%#
 Copyright 2013-2017 the original author or authors.

 This file is part of the JHipster project, see https://jhipster.github.io/
 for more information.

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
-%>
package <%=packageName%>.service<% if (service == 'serviceImpl') { %>.impl<% } %>;
<%  const serviceClassName = service == 'serviceImpl' ? entityClass + 'ServiceImpl' : entityClass + 'Service';
    let viaService = false;
    const instanceType = (dto == 'mapstruct') ? entityClass + 'DTO' : entityClass;
    const instanceName = (dto == 'mapstruct') ? entityInstance + 'DTO' : entityInstance;
    const mapper = entityInstance  + 'Mapper';
    const dtoToEntity = mapper + '.'+ entityInstance +'DTOTo' + entityClass;
    const entityToDto = mapper + '.'+ entityInstance +'To' + entityClass + 'DTO';
    const entityToDtoReference = mapper + '::'+ entityInstance +'To' + entityClass + 'DTO';
    const repository = entityInstance  + 'Repository';
    const searchRepository = entityInstance  + 'SearchRepository';
    if (service == 'serviceImpl') { %>
import <%=packageName%>.service.<%= entityClass %>Service;<% } %>
import <%=packageName%>.domain.<%= entityClass %>;
import <%=packageName%>.repository.<%= entityClass %>Repository;<% if (searchEngine == 'elasticsearch') { %>
import <%=packageName%>.repository.search.<%= entityClass %>SearchRepository;<% } if (dto == 'mapstruct') { %>
import <%=packageName%>.service.dto.<%= entityClass %>DTO;
import <%=packageName%>.service.mapper.<%= entityClass %>Mapper;<% } %>
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;<% if (pagination != 'no') { %>
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;<% } if (databaseType == 'sql') { %>
import org.springframework.transaction.annotation.Transactional;<% } %>
import org.springframework.stereotype.Service;
<% if (dto == 'mapstruct') { %>
import java.util.LinkedList;<% } %>
import java.util.List;<% if (databaseType == 'cassandra') { %>
import java.util.UUID;<% } %><% if (searchEngine == 'elasticsearch' || dto == 'mapstruct' || fieldsContainNoOwnerOneToOne == true) { %>
import java.util.stream.Collectors;<% } %><% if (searchEngine == 'elasticsearch' || fieldsContainNoOwnerOneToOne == true) { %>
import java.util.stream.StreamSupport;<% } %><% if (searchEngine == 'elasticsearch') { %>

import static org.elasticsearch.index.query.QueryBuilders.*;<% } %>

/**
 * Service Implementation for managing <%= entityClass %>.
 */
@Service<% if (databaseType == 'sql') { %>
@Transactional<% } %>
public class <%= serviceClassName %> extends AbstractDomainServiceImpl< <%= entityClass %>, Long> <% if (service == 'serviceImpl') { %>implements <%= entityClass %>Service<% } %>{

    private final Logger LOGGER = LoggerFactory.getLogger(<%= serviceClassName %>.class);
    private final <%= entityClass %>Repository <%= entityInstance %>Repository;
    private final <%= entityClass %>SearchRepository <%= entityInstance %>SearchRepository;
    
    public <%= serviceClassName %>(<%= entityClass %>Repository <%= entityInstance %>Repository, <%= entityClass %>SearchRepository <%= entityInstance %>SearchRepository) {
        super(<%= entityInstance %>Repository,<%= entityInstance %>SearchRepository);
        this.<%= entityInstance %>Repository = <%= entityInstance %>Repository;
        this.<%= entityInstance %>SearchRepository = <%= entityInstance %>SearchRepository;
    }
    
}
