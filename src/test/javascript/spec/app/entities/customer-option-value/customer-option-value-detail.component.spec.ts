import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { EshopTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CustomerOptionValueDetailComponent } from '../../../../../../main/webapp/app/entities/customer-option-value/customer-option-value-detail.component';
import { CustomerOptionValueService } from '../../../../../../main/webapp/app/entities/customer-option-value/customer-option-value.service';
import { CustomerOptionValue } from '../../../../../../main/webapp/app/entities/customer-option-value/customer-option-value.model';

describe('Component Tests', () => {

    describe('CustomerOptionValue Management Detail Component', () => {
        let comp: CustomerOptionValueDetailComponent;
        let fixture: ComponentFixture<CustomerOptionValueDetailComponent>;
        let service: CustomerOptionValueService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EshopTestModule],
                declarations: [CustomerOptionValueDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    CustomerOptionValueService,
                    EventManager
                ]
            }).overrideComponent(CustomerOptionValueDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CustomerOptionValueDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CustomerOptionValueService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new CustomerOptionValue(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.customerOptionValue).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
