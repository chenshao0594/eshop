import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { EshopTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ProductRelationshipDetailComponent } from '../../../../../../main/webapp/app/entities/product-relationship/product-relationship-detail.component';
import { ProductRelationshipService } from '../../../../../../main/webapp/app/entities/product-relationship/product-relationship.service';
import { ProductRelationship } from '../../../../../../main/webapp/app/entities/product-relationship/product-relationship.model';

describe('Component Tests', () => {

    describe('ProductRelationship Management Detail Component', () => {
        let comp: ProductRelationshipDetailComponent;
        let fixture: ComponentFixture<ProductRelationshipDetailComponent>;
        let service: ProductRelationshipService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EshopTestModule],
                declarations: [ProductRelationshipDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ProductRelationshipService,
                    EventManager
                ]
            }).overrideComponent(ProductRelationshipDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ProductRelationshipDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProductRelationshipService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ProductRelationship(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.productRelationship).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
