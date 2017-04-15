import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { EshopTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { OrderAccountDetailComponent } from '../../../../../../main/webapp/app/entities/order-account/order-account-detail.component';
import { OrderAccountService } from '../../../../../../main/webapp/app/entities/order-account/order-account.service';
import { OrderAccount } from '../../../../../../main/webapp/app/entities/order-account/order-account.model';

describe('Component Tests', () => {

    describe('OrderAccount Management Detail Component', () => {
        let comp: OrderAccountDetailComponent;
        let fixture: ComponentFixture<OrderAccountDetailComponent>;
        let service: OrderAccountService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EshopTestModule],
                declarations: [OrderAccountDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    OrderAccountService,
                    EventManager
                ]
            }).overrideComponent(OrderAccountDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OrderAccountDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OrderAccountService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new OrderAccount(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.orderAccount).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
