import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { EshopTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { OrderProductDetailComponent } from '../../../../../../main/webapp/app/entities/order-product/order-product-detail.component';
import { OrderProductService } from '../../../../../../main/webapp/app/entities/order-product/order-product.service';
import { OrderProduct } from '../../../../../../main/webapp/app/entities/order-product/order-product.model';

describe('Component Tests', () => {

    describe('OrderProduct Management Detail Component', () => {
        let comp: OrderProductDetailComponent;
        let fixture: ComponentFixture<OrderProductDetailComponent>;
        let service: OrderProductService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EshopTestModule],
                declarations: [OrderProductDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    OrderProductService,
                    EventManager
                ]
            }).overrideComponent(OrderProductDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OrderProductDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OrderProductService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new OrderProduct(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.orderProduct).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
