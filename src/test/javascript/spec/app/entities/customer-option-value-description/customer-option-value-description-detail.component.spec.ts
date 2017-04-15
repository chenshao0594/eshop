import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { EshopTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CustomerOptionValueDescriptionDetailComponent } from '../../../../../../main/webapp/app/entities/customer-option-value-description/customer-option-value-description-detail.component';
import { CustomerOptionValueDescriptionService } from '../../../../../../main/webapp/app/entities/customer-option-value-description/customer-option-value-description.service';
import { CustomerOptionValueDescription } from '../../../../../../main/webapp/app/entities/customer-option-value-description/customer-option-value-description.model';

describe('Component Tests', () => {

    describe('CustomerOptionValueDescription Management Detail Component', () => {
        let comp: CustomerOptionValueDescriptionDetailComponent;
        let fixture: ComponentFixture<CustomerOptionValueDescriptionDetailComponent>;
        let service: CustomerOptionValueDescriptionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EshopTestModule],
                declarations: [CustomerOptionValueDescriptionDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    CustomerOptionValueDescriptionService,
                    EventManager
                ]
            }).overrideComponent(CustomerOptionValueDescriptionDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CustomerOptionValueDescriptionDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CustomerOptionValueDescriptionService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new CustomerOptionValueDescription(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.customerOptionValueDescription).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
