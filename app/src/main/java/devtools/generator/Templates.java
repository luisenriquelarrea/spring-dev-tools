package devtools.generator;

public class Templates {
    private String modeloTemplate;
    private String dtoTemplate;
    private String mapperTemplate;
    private String specificationTemplate;
    private String repositoryTemplate;
    private String serviceTemplate;
    private String serviceImplTemplate;
    private String controllerTemplate;
    
    public Templates(){
        setModeloTemplate();
        setDtoTemplate();
        setMapperTemplate();
        setSpecificationTemplate();
        setRepositoryTemplate();
        setServiceTemplate();
        setServiceImplTemplate();
        setControllerTemplate();
    }
    
    private void setModeloTemplate(){
        modeloTemplate = """
                         package com.packageName.artifactName.model;
                         
                         import com.packageName.artifactName.model.base.EmpresaAuditable;

                         import jakarta.persistence.Entity;
                         import jakarta.persistence.Table;
                         import jakarta.persistence.GeneratedValue;
                         import jakarta.persistence.GenerationType;
                         import jakarta.persistence.Id;
                         import lombok.Getter;
                         import lombok.RequiredArgsConstructor;
                         import lombok.Setter;
                         
                         @Entity
                         @Table(name = "tableName")
                         @RequiredArgsConstructor
                         @Getter
                         @Setter
                         public class className extends EmpresaAuditable {
                             @Id
                             @GeneratedValue(strategy=GenerationType.IDENTITY)
                             private Long id;

                             private String descripcion;
                         }""";
    }
    
    public String getModeloTemplate(){
        return modeloTemplate;
    }
    
    private void setDtoTemplate(){
        dtoTemplate = """
                         package com.packageName.artifactName.dto;
                      
                         import java.time.LocalDateTime;
                         
                         import lombok.Data;
                         
                         @Data
                         public class classNameDto {
                             private Long id;

                             private String descripcion;
                         
                             private int status;
                         
                             private LocalDateTime createdAt;
                             
                             private LocalDateTime updatedAt;
                             
                             private Integer userCreatedId;
                             
                             private Integer userUpdatedId;
                         
                             private int offset;
                                           
                             private int limit;
                         }""";
    }
    
    public String getDtoTemplate(){
        return dtoTemplate;
    }
    
    private void setMapperTemplate(){
        mapperTemplate = """
                         package com.packageName.artifactName.mapper;
                         
                         import org.mapstruct.BeanMapping;
                         import org.mapstruct.Mapper;
                         import org.mapstruct.MappingTarget;
                         import org.mapstruct.NullValuePropertyMappingStrategy;
                         
                         import com.packageName.artifactName.model.className;
                         import com.packageName.artifactName.dto.classNameDto;
                         
                         @Mapper(componentModel = "spring")
                         public interface classNameMapper extends BaseMapper<className, classNameDto> {
                             @Override
                             @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
                             void updateEntityFromDto(classNameDto dto, @MappingTarget className entity);
                         }
                         """;
    }
    
    public String getMapperTemplate(){
        return mapperTemplate;
    }
    
    private void setSpecificationTemplate(){
        specificationTemplate = """
                                package com.packageName.artifactName.specifications;
                                
                                import java.util.List;
                                import java.util.ArrayList;
                                
                                import org.springframework.data.jpa.domain.Specification;
                                
                                import com.packageName.artifactName.model.className;
                                import com.packageName.artifactName.dto.classNameDto;
                                
                                import jakarta.persistence.criteria.CriteriaBuilder;
                                import jakarta.persistence.criteria.CriteriaQuery;
                                import jakarta.persistence.criteria.Predicate;
                                import jakarta.persistence.criteria.Root;
                                
                                public class classNameSpecifications implements Specification<className>{
                                    private classNameDto objNameDto = null;
                                
                                    public classNameSpecifications(classNameDto objNameDto){
                                        this.objNameDto = objNameDto;
                                    }
                                
                                    @Override
                                    public Predicate toPredicate(Root<className> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                                        List<Predicate> predicates = new ArrayList<Predicate>();
                                        
                                        return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
                                    }
                                }
                                """;
    }
    
    public String getSpecificationTemplate(){
        return specificationTemplate;
    }
    
    private void setRepositoryTemplate(){
        repositoryTemplate = """
                             package com.packageName.artifactName.repository;
                             
                             import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
                             import org.springframework.data.jpa.repository.JpaRepository;
                             
                             import com.packageName.artifactName.model.className;
                             
                             public interface classNameRepository extends JpaRepository<className, Long>, JpaSpecificationExecutor<className> {
                                 
                             }
                             """;
    }
    
    public String getRepositoryTemplate(){
        return repositoryTemplate;
    }
    
    private void setServiceTemplate(){
        serviceTemplate = """
                          package com.packageName.artifactName.service;
                          
                          import java.util.List;
                          
                          import org.springframework.data.domain.PageRequest;
                          import org.springframework.data.jpa.domain.Specification;
                          
                          import com.fasterxml.jackson.databind.JsonNode;
                          
                          import com.packageName.artifactName.dto.classNameDto;
                          import com.packageName.artifactName.model.className;
                          
                          public interface classNameService {
                              //Create operation
                              className save(className objName);
                          
                              // Save a list of entities in batch
                              List<className> saveAll(List<className> objName);
                          
                              //Patch operation
                              classNameDto patch(Long id, JsonNode patchNode);
                              
                              //Read operation
                              List<className> list();
                          
                              //Delete operation
                              void deleteById(Long id);
                          
                              //Exists by id operation
                              boolean existsById(Long id);
                          
                              //Get entity by id
                              className getById(Long id);
                              
                              //Read operation filtered by specifications
                              List<classNameDto> filteredList(Specification<className> specs, PageRequest pageRequest);
                              
                              //Count entity records
                              long count();
                              
                              //Count entity records with filter
                              long countFilteredList(Specification<className> specs);
                          }
                          """;
    }
    
    public String getServiceTemplate(){
        return serviceTemplate;
    }
    
    private void setServiceImplTemplate(){
        serviceImplTemplate = """
                              package com.packageName.artifactName.service;
                              
                              import org.springframework.beans.factory.annotation.Autowired;
                              import org.springframework.data.domain.Page;
                              import org.springframework.data.domain.PageRequest;
                              import org.springframework.data.jpa.domain.Specification;
                              import org.springframework.stereotype.Service;
                              
                              import com.packageName.artifactName.dto.classNameDto;
                              import com.packageName.artifactName.mapper.classNameMapper;
                              import com.packageName.artifactName.model.className;
                              import com.packageName.artifactName.repository.classNameRepository;
                              import com.packageName.artifactName.utils.ObjectMapperUtils;
                              
                              import com.fasterxml.jackson.databind.JsonNode;
                              
                              import java.util.List;
                              
                              @Service
                              public class classNameServiceImpl extends BaseServiceImpl implements classNameService {
                                  @Autowired 
                                  private classNameRepository repository;
                              
                                  @Autowired
                                  private classNameMapper mapper;
                              
                                  @Override
                                  public className save(className objName) {
                                      return repository.save(objName);
                                  }
                              
                                  @Override
                                  public List<className> saveAll(List<className> objName){
                                      return repository.saveAll(objName);
                                  }
                              
                                  @Override
                                  public classNameDto patch(Long id, JsonNode patchNode) {
                                      className entity = repository.findById(id)
                                          .orElseThrow();
                                      classNameDto originalDto = mapper.toDto(entity);
                                      classNameDto patchedDto = applyMergePatchNode(originalDto, patchNode, classNameDto.class);
                                      mapper.updateEntityFromDto(patchedDto, entity);
                                      repository.save(entity);
                                      return mapper.toDto(entity);
                                  }
                              
                                  @Override
                                  public List<className> list(){
                                      return (List<className>) repository.findAll();
                                  }
                              
                                  @Override
                                  public void deleteById(Long id){
                                      repository.deleteById(id);
                                  }
                              
                                  @Override
                                  public boolean existsById(Long id) {
                                      return repository.existsById(id);
                                  }
                              
                                  @Override
                                  public className getById(Long id){
                                      return repository.findById(id).get();
                                  }
                                  
                                  @Override
                                  public List<classNameDto> filteredList(Specification<className> specs, PageRequest pageRequest){
                                      Page<className> objNamePage = repository.findAll(specs,
                                          pageRequest);
                                      List<className> objName = objNamePage.getContent();
                                      return (List<classNameDto>) 
                                          ObjectMapperUtils.mapAll(objName, classNameDto.class);
                                  }
                                  
                                  @Override
                                  public long count(){
                                      return repository.count();
                                  }
                                  
                                  @Override
                                  public long countFilteredList(Specification<className> specs){
                                      return repository.count(specs);
                                  }
                              }
                              """;
    }
    
    public String getServiceImplTemplate(){
        return serviceImplTemplate;
    }
    
    private void setControllerTemplate(){
        controllerTemplate = """
                             package com.packageName.artifactName.controller;
                             
                             import org.springframework.beans.factory.annotation.Autowired;
                             import org.springframework.http.HttpStatus;
                             import org.springframework.http.ResponseEntity;
                             import org.springframework.data.domain.PageRequest;
                             import org.springframework.data.jpa.domain.Specification;
                             import org.springframework.web.bind.annotation.CrossOrigin;
                             import org.springframework.web.bind.annotation.DeleteMapping;
                             import org.springframework.web.bind.annotation.RestController;
                             import org.springframework.web.bind.annotation.GetMapping;
                             import org.springframework.web.bind.annotation.PatchMapping;
                             import org.springframework.web.bind.annotation.PathVariable;
                             import org.springframework.web.bind.annotation.PostMapping;
                             import org.springframework.web.bind.annotation.PutMapping;
                             import org.springframework.web.bind.annotation.RequestBody;
                             import org.springframework.web.bind.annotation.RequestMapping;
                             import org.springframework.web.bind.annotation.ResponseBody;
                             
                             import com.packageName.artifactName.dto.classNameDto;
                             import com.packageName.artifactName.errorhandling.BusinessException;
                             import com.packageName.artifactName.model.className;
                             import com.packageName.artifactName.specifications.classNameSpecifications;
                             import com.packageName.artifactName.service.classNameService;
                             
                             import jakarta.persistence.OptimisticLockException;
                             
                             import com.fasterxml.jackson.databind.JsonNode;
                             
                             import java.util.List;
                             import java.util.Map;
                             
                             @CrossOrigin(origins = "*")
                             @RestController
                             @RequestMapping(path="/api/tableName")
                             public class classNameController {
                                 @Autowired 
                                 private classNameService objNameService;
                             
                                 @PostMapping(path="/add")
                                 public ResponseEntity<className> add(@RequestBody className objName) {
                                     className obj = objNameService.save(objName);
                                     return ResponseEntity.ok(obj);
                                 }
                             
                                 @PostMapping(path="/addAll")
                                 public ResponseEntity<Object> addAll(@RequestBody List<className> objNameList) {
                                     if (objNameList == null || objNameList.isEmpty())
                                         return ResponseEntity.badRequest().body(Map.of("message", "Sin informacion a guardar."));
                                     try {
                                         List<className> saved = objNameService.saveAll(objNameList);
                                         return ResponseEntity.ok(saved);
                                     } catch (BusinessException ex) {
                                         return ResponseEntity.badRequest().body(Map.of("message", ex.getMessage()));
                                     } catch (OptimisticLockException ex) {
                                         return ResponseEntity.status(HttpStatus.CONFLICT)
                                             .body(Map.of("message", "El registro fue modificado por otro usuario. Recarga y vuelve a intentar."));
                                     } catch (Exception ex) {
                                         ex.printStackTrace();
                                         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                             .body(Map.of("message", "Error al guardar los registros: " + ex.getMessage()));
                                     }
                                 }
                             
                                 @PatchMapping(value = "/{id}", consumes = "application/merge-patch+json")
                                 public ResponseEntity<classNameDto> patch(@PathVariable Long id, 
                                         @RequestBody JsonNode patchNode) {
                                     classNameDto objNameDto = objNameService.patch(id, patchNode);
                                     return ResponseEntity.ok(objNameDto);
                                 }
                             
                                 @PutMapping(path="/{id}")
                                 public ResponseEntity<className> update(@RequestBody className objName,
                                                  @PathVariable Long id){
                                     className obj = objNameService.save(objName);
                                     return ResponseEntity.ok(obj);
                                 }
                             
                                 @GetMapping(path="/")
                                 public @ResponseBody List<className> all() {
                                     return objNameService.list();
                                 }
                             
                                 @DeleteMapping(path="/{id}")
                                 public ResponseEntity<?> deleteById(@PathVariable String id) {
                                     Long objNameId;
                                     try {
                                         objNameId = Long.parseLong(id);
                                     } catch (NumberFormatException e) {
                                         return ResponseEntity.badRequest().body(Map.of(
                                             "message", "El ID proporcionado no es valido."
                                         ));
                                     }
                                     if (!objNameService.existsById(objNameId))
                                         return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                                             "message", "ID no encontrado."
                                         ));
                                     objNameService.deleteById(objNameId);
                                     return ResponseEntity.ok("Deleted");
                                 }
                             
                                 @GetMapping(path="/{id}")
                                 public ResponseEntity<className> getById(@PathVariable Long id){
                                     if(objNameService.existsById(id)){
                                         className objName = objNameService.getById(id);
                                         return ResponseEntity.ok(objName);
                                     }
                                     return ResponseEntity.notFound().build();
                                 }
                                 
                                 @PostMapping(path="/filteredList")
                                 public @ResponseBody List<classNameDto> filteredList(@RequestBody classNameDto objNameDto) {
                                     Specification<className> specs = new classNameSpecifications(objNameDto);

                                     int offset = objNameDto.getOffset();
                                     int limit = objNameDto.getLimit();
                                     int page = offset / limit;

                                     return objNameService.filteredList(specs, PageRequest.of(page, limit));
                                 }
                                 
                                 @GetMapping(path="/count")
                                 public ResponseEntity<Long> count() {
                                     return ResponseEntity.ok(objNameService.count());
                                 }
                                 
                                 @PostMapping(path="/countFilteredList")
                                 public ResponseEntity<Long> countFilteredList(@RequestBody classNameDto objNameDto) {
                                     Specification<className> specs = new classNameSpecifications(objNameDto);
                                     return ResponseEntity.ok(objNameService.countFilteredList(specs));
                                 }
                             }
                             """;
    }
    
    public String getControllerTemplate(){
        return controllerTemplate;
    }
}
