import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { EshopTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CustomerOptionSetDetailComponent } from '../../../../../../main/webapp/app/entities/customer-option-set/customer-option-set-detail.component';
import { CustomerOptionSetService } from '../../../../../../main/webapp/app/entities/customer-option-set/customer-option-set.service';
import { CustomerOptionSet } from '../../../../../../main/webapp/app/entities/customer-option-set/customer-option-set.model';

describe('Component Tests', () => {

    describe('CustomerOptionSet Management Detail Component', () => {
        let comp: CustomerOptionSetDetailComponent;
        let fixture: ComponentFixture<CustomerOptionSetDetailComponent>;
        let service: CustomerOptionSetService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EshopTestModule],
                declarations: [CustomerOptionSetDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    CustomerOptionSetService,
                    EventManager
                ]
            }).overrideComponent(CustomerOptionSetDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CustomerOptionSetDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CustomerOptionSetService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new CustomerOptionSet(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.customerOptionSet).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
