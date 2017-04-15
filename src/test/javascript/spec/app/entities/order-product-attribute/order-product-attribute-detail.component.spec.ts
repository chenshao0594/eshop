import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { EshopTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { OrderProductAttributeDetailComponent } from '../../../../../../main/webapp/app/entities/order-product-attribute/order-product-attribute-detail.component';
import { OrderProductAttributeService } from '../../../../../../main/webapp/app/entities/order-product-attribute/order-product-attribute.service';
import { OrderProductAttribute } from '../../../../../../main/webapp/app/entities/order-product-attribute/order-product-attribute.model';

describe('Component Tests', () => {

    describe('OrderProductAttribute Management Detail Component', () => {
        let comp: OrderProductAttributeDetailComponent;
        let fixture: ComponentFixture<OrderProductAttributeDetailComponent>;
        let service: OrderProductAttributeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EshopTestModule],
                declarations: [OrderProductAttributeDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    OrderProductAttributeService,
                    EventManager
                ]
            }).overrideComponent(OrderProductAttributeDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OrderProductAttributeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OrderProductAttributeService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new OrderProductAttribute(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.orderProductAttribute).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
