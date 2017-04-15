import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { EshopTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { OrderStatusHistoryDetailComponent } from '../../../../../../main/webapp/app/entities/order-status-history/order-status-history-detail.component';
import { OrderStatusHistoryService } from '../../../../../../main/webapp/app/entities/order-status-history/order-status-history.service';
import { OrderStatusHistory } from '../../../../../../main/webapp/app/entities/order-status-history/order-status-history.model';

describe('Component Tests', () => {

    describe('OrderStatusHistory Management Detail Component', () => {
        let comp: OrderStatusHistoryDetailComponent;
        let fixture: ComponentFixture<OrderStatusHistoryDetailComponent>;
        let service: OrderStatusHistoryService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EshopTestModule],
                declarations: [OrderStatusHistoryDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    OrderStatusHistoryService,
                    EventManager
                ]
            }).overrideComponent(OrderStatusHistoryDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OrderStatusHistoryDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OrderStatusHistoryService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new OrderStatusHistory(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.orderStatusHistory).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
