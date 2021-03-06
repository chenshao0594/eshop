import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { EshopTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { SalesOrderDetailComponent } from '../../../../../../main/webapp/app/entities/sales-order/sales-order-detail.component';
import { SalesOrderService } from '../../../../../../main/webapp/app/entities/sales-order/sales-order.service';
import { SalesOrder } from '../../../../../../main/webapp/app/entities/sales-order/sales-order.model';

describe('Component Tests', () => {

    describe('SalesOrder Management Detail Component', () => {
        let comp: SalesOrderDetailComponent;
        let fixture: ComponentFixture<SalesOrderDetailComponent>;
        let service: SalesOrderService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EshopTestModule],
                declarations: [SalesOrderDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    SalesOrderService,
                    EventManager
                ]
            }).overrideComponent(SalesOrderDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SalesOrderDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SalesOrderService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new SalesOrder(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.salesOrder).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
