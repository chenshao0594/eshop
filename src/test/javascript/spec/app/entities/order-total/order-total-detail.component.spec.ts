import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { EshopTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { OrderTotalDetailComponent } from '../../../../../../main/webapp/app/entities/order-total/order-total-detail.component';
import { OrderTotalService } from '../../../../../../main/webapp/app/entities/order-total/order-total.service';
import { OrderTotal } from '../../../../../../main/webapp/app/entities/order-total/order-total.model';

describe('Component Tests', () => {

    describe('OrderTotal Management Detail Component', () => {
        let comp: OrderTotalDetailComponent;
        let fixture: ComponentFixture<OrderTotalDetailComponent>;
        let service: OrderTotalService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EshopTestModule],
                declarations: [OrderTotalDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    OrderTotalService,
                    EventManager
                ]
            }).overrideComponent(OrderTotalDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OrderTotalDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OrderTotalService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new OrderTotal(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.orderTotal).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
