import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { EshopTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ProductAttributeDetailComponent } from '../../../../../../main/webapp/app/entities/product-attribute/product-attribute-detail.component';
import { ProductAttributeService } from '../../../../../../main/webapp/app/entities/product-attribute/product-attribute.service';
import { ProductAttribute } from '../../../../../../main/webapp/app/entities/product-attribute/product-attribute.model';

describe('Component Tests', () => {

    describe('ProductAttribute Management Detail Component', () => {
        let comp: ProductAttributeDetailComponent;
        let fixture: ComponentFixture<ProductAttributeDetailComponent>;
        let service: ProductAttributeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EshopTestModule],
                declarations: [ProductAttributeDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ProductAttributeService,
                    EventManager
                ]
            }).overrideComponent(ProductAttributeDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ProductAttributeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProductAttributeService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ProductAttribute(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.productAttribute).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
