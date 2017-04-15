import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { EshopTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ProductPriceDescriptionDetailComponent } from '../../../../../../main/webapp/app/entities/product-price-description/product-price-description-detail.component';
import { ProductPriceDescriptionService } from '../../../../../../main/webapp/app/entities/product-price-description/product-price-description.service';
import { ProductPriceDescription } from '../../../../../../main/webapp/app/entities/product-price-description/product-price-description.model';

describe('Component Tests', () => {

    describe('ProductPriceDescription Management Detail Component', () => {
        let comp: ProductPriceDescriptionDetailComponent;
        let fixture: ComponentFixture<ProductPriceDescriptionDetailComponent>;
        let service: ProductPriceDescriptionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EshopTestModule],
                declarations: [ProductPriceDescriptionDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ProductPriceDescriptionService,
                    EventManager
                ]
            }).overrideComponent(ProductPriceDescriptionDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ProductPriceDescriptionDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProductPriceDescriptionService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ProductPriceDescription(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.productPriceDescription).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
