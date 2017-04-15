import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { EshopTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ProductPriceDetailComponent } from '../../../../../../main/webapp/app/entities/product-price/product-price-detail.component';
import { ProductPriceService } from '../../../../../../main/webapp/app/entities/product-price/product-price.service';
import { ProductPrice } from '../../../../../../main/webapp/app/entities/product-price/product-price.model';

describe('Component Tests', () => {

    describe('ProductPrice Management Detail Component', () => {
        let comp: ProductPriceDetailComponent;
        let fixture: ComponentFixture<ProductPriceDetailComponent>;
        let service: ProductPriceService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EshopTestModule],
                declarations: [ProductPriceDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ProductPriceService,
                    EventManager
                ]
            }).overrideComponent(ProductPriceDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ProductPriceDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProductPriceService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ProductPrice(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.productPrice).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
