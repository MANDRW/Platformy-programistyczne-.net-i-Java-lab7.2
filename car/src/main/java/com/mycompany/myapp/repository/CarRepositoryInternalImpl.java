package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Car;
import com.mycompany.myapp.repository.rowmapper.CarRowMapper;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.convert.R2dbcConverter;
import org.springframework.data.r2dbc.core.R2dbcEntityOperations;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.r2dbc.repository.support.SimpleR2dbcRepository;
import org.springframework.data.relational.core.sql.Comparison;
import org.springframework.data.relational.core.sql.Condition;
import org.springframework.data.relational.core.sql.Conditions;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Select;
import org.springframework.data.relational.core.sql.SelectBuilder.SelectFromAndJoin;
import org.springframework.data.relational.core.sql.Table;
import org.springframework.data.relational.repository.support.MappingRelationalEntityInformation;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.r2dbc.core.RowsFetchSpec;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC custom repository implementation for the Car entity.
 */
@SuppressWarnings("unused")
class CarRepositoryInternalImpl extends SimpleR2dbcRepository<Car, Long> implements CarRepositoryInternal {

    private final DatabaseClient db;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final EntityManager entityManager;

    private final CarRowMapper carMapper;

    private static final Table entityTable = Table.aliased("car", EntityManager.ENTITY_ALIAS);

    public CarRepositoryInternalImpl(
        R2dbcEntityTemplate template,
        EntityManager entityManager,
        CarRowMapper carMapper,
        R2dbcEntityOperations entityOperations,
        R2dbcConverter converter
    ) {
        super(
            new MappingRelationalEntityInformation(converter.getMappingContext().getRequiredPersistentEntity(Car.class)),
            entityOperations,
            converter
        );
        this.db = template.getDatabaseClient();
        this.r2dbcEntityTemplate = template;
        this.entityManager = entityManager;
        this.carMapper = carMapper;
    }

    @Override
    public Flux<Car> findAllBy(Pageable pageable) {
        return createQuery(pageable, null).all();
    }

    RowsFetchSpec<Car> createQuery(Pageable pageable, Condition whereClause) {
        List<Expression> columns = CarSqlHelper.getColumns(entityTable, EntityManager.ENTITY_ALIAS);
        SelectFromAndJoin selectFrom = Select.builder().select(columns).from(entityTable);
        // we do not support Criteria here for now as of https://github.com/jhipster/generator-jhipster/issues/18269
        String select = entityManager.createSelect(selectFrom, Car.class, pageable, whereClause);
        return db.sql(select).map(this::process);
    }

    @Override
    public Flux<Car> findAll() {
        return findAllBy(null);
    }

    @Override
    public Mono<Car> findById(Long id) {
        Comparison whereClause = Conditions.isEqual(entityTable.column("id"), Conditions.just(id.toString()));
        return createQuery(null, whereClause).one();
    }

    private Car process(Row row, RowMetadata metadata) {
        Car entity = carMapper.apply(row, "e");
        return entity;
    }

    @Override
    public <S extends Car> Mono<S> save(S entity) {
        return super.save(entity);
    }
}
