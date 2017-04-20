import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { EshopTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { BillingDetailComponent } from '../../../../../../main/webapp/app/entities/billing/billing-detail.component';
import { BillingService } from '../../../../../../main/webapp/app/entities/billing/billing.service';
import { Billing } from '../../../../../../main/webapp/app/entities/billing/billing.model';

describe('Component Tests', () => {

    describe('Billing Management Detail Component', () => {
        let comp: BillingDetailComponent;
        let fixture: ComponentFixture<BillingDetailComponent>;
        let service: BillingService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EshopTestModule],
                declarations: [BillingDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    BillingService,
                    EventManager
                ]
            }).overrideComponent(BillingDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BillingDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BillingService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Billing(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.billing).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
