import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { EshopTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ProductOptionValueDetailComponent } from '../../../../../../main/webapp/app/entities/product-option-value/product-option-value-detail.component';
import { ProductOptionValueService } from '../../../../../../main/webapp/app/entities/product-option-value/product-option-value.service';
import { ProductOptionValue } from '../../../../../../main/webapp/app/entities/product-option-value/product-option-value.model';

describe('Component Tests', () => {

    describe('ProductOptionValue Management Detail Component', () => {
        let comp: ProductOptionValueDetailComponent;
        let fixture: ComponentFixture<ProductOptionValueDetailComponent>;
        let service: ProductOptionValueService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EshopTestModule],
                declarations: [ProductOptionValueDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ProductOptionValueService,
                    EventManager
                ]
            }).overrideComponent(ProductOptionValueDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ProductOptionValueDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProductOptionValueService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ProductOptionValue(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.productOptionValue).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
