import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { EshopTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ProductOptionDetailComponent } from '../../../../../../main/webapp/app/entities/product-option/product-option-detail.component';
import { ProductOptionService } from '../../../../../../main/webapp/app/entities/product-option/product-option.service';
import { ProductOption } from '../../../../../../main/webapp/app/entities/product-option/product-option.model';

describe('Component Tests', () => {

    describe('ProductOption Management Detail Component', () => {
        let comp: ProductOptionDetailComponent;
        let fixture: ComponentFixture<ProductOptionDetailComponent>;
        let service: ProductOptionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EshopTestModule],
                declarations: [ProductOptionDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ProductOptionService,
                    EventManager
                ]
            }).overrideComponent(ProductOptionDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ProductOptionDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProductOptionService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ProductOption(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.productOption).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
