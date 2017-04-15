import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { EshopTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CustomerOptionDescriptionDetailComponent } from '../../../../../../main/webapp/app/entities/customer-option-description/customer-option-description-detail.component';
import { CustomerOptionDescriptionService } from '../../../../../../main/webapp/app/entities/customer-option-description/customer-option-description.service';
import { CustomerOptionDescription } from '../../../../../../main/webapp/app/entities/customer-option-description/customer-option-description.model';

describe('Component Tests', () => {

    describe('CustomerOptionDescription Management Detail Component', () => {
        let comp: CustomerOptionDescriptionDetailComponent;
        let fixture: ComponentFixture<CustomerOptionDescriptionDetailComponent>;
        let service: CustomerOptionDescriptionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EshopTestModule],
                declarations: [CustomerOptionDescriptionDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    CustomerOptionDescriptionService,
                    EventManager
                ]
            }).overrideComponent(CustomerOptionDescriptionDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CustomerOptionDescriptionDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CustomerOptionDescriptionService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new CustomerOptionDescription(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.customerOptionDescription).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
