import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { EshopTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CustomerOptinDetailComponent } from '../../../../../../main/webapp/app/entities/customer-optin/customer-optin-detail.component';
import { CustomerOptinService } from '../../../../../../main/webapp/app/entities/customer-optin/customer-optin.service';
import { CustomerOptin } from '../../../../../../main/webapp/app/entities/customer-optin/customer-optin.model';

describe('Component Tests', () => {

    describe('CustomerOptin Management Detail Component', () => {
        let comp: CustomerOptinDetailComponent;
        let fixture: ComponentFixture<CustomerOptinDetailComponent>;
        let service: CustomerOptinService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EshopTestModule],
                declarations: [CustomerOptinDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    CustomerOptinService,
                    EventManager
                ]
            }).overrideComponent(CustomerOptinDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CustomerOptinDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CustomerOptinService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new CustomerOptin(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.customerOptin).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
