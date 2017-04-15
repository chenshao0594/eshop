import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { EshopTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { OrderProductPriceDetailComponent } from '../../../../../../main/webapp/app/entities/order-product-price/order-product-price-detail.component';
import { OrderProductPriceService } from '../../../../../../main/webapp/app/entities/order-product-price/order-product-price.service';
import { OrderProductPrice } from '../../../../../../main/webapp/app/entities/order-product-price/order-product-price.model';

describe('Component Tests', () => {

    describe('OrderProductPrice Management Detail Component', () => {
        let comp: OrderProductPriceDetailComponent;
        let fixture: ComponentFixture<OrderProductPriceDetailComponent>;
        let service: OrderProductPriceService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EshopTestModule],
                declarations: [OrderProductPriceDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    OrderProductPriceService,
                    EventManager
                ]
            }).overrideComponent(OrderProductPriceDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OrderProductPriceDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OrderProductPriceService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new OrderProductPrice(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.orderProductPrice).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
