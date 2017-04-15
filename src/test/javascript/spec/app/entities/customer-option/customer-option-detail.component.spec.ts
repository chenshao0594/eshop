import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { EshopTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CustomerOptionDetailComponent } from '../../../../../../main/webapp/app/entities/customer-option/customer-option-detail.component';
import { CustomerOptionService } from '../../../../../../main/webapp/app/entities/customer-option/customer-option.service';
import { CustomerOption } from '../../../../../../main/webapp/app/entities/customer-option/customer-option.model';

describe('Component Tests', () => {

    describe('CustomerOption Management Detail Component', () => {
        let comp: CustomerOptionDetailComponent;
        let fixture: ComponentFixture<CustomerOptionDetailComponent>;
        let service: CustomerOptionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EshopTestModule],
                declarations: [CustomerOptionDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    CustomerOptionService,
                    EventManager
                ]
            }).overrideComponent(CustomerOptionDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CustomerOptionDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CustomerOptionService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new CustomerOption(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.customerOption).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
