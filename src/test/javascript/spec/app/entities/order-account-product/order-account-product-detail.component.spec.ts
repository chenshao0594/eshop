import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { EshopTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { OrderAccountProductDetailComponent } from '../../../../../../main/webapp/app/entities/order-account-product/order-account-product-detail.component';
import { OrderAccountProductService } from '../../../../../../main/webapp/app/entities/order-account-product/order-account-product.service';
import { OrderAccountProduct } from '../../../../../../main/webapp/app/entities/order-account-product/order-account-product.model';

describe('Component Tests', () => {

    describe('OrderAccountProduct Management Detail Component', () => {
        let comp: OrderAccountProductDetailComponent;
        let fixture: ComponentFixture<OrderAccountProductDetailComponent>;
        let service: OrderAccountProductService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EshopTestModule],
                declarations: [OrderAccountProductDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    OrderAccountProductService,
                    EventManager
                ]
            }).overrideComponent(OrderAccountProductDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OrderAccountProductDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OrderAccountProductService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new OrderAccountProduct(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.orderAccountProduct).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
