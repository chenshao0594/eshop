import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { EshopTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CustomerAttributeDetailComponent } from '../../../../../../main/webapp/app/entities/customer-attribute/customer-attribute-detail.component';
import { CustomerAttributeService } from '../../../../../../main/webapp/app/entities/customer-attribute/customer-attribute.service';
import { CustomerAttribute } from '../../../../../../main/webapp/app/entities/customer-attribute/customer-attribute.model';

describe('Component Tests', () => {

    describe('CustomerAttribute Management Detail Component', () => {
        let comp: CustomerAttributeDetailComponent;
        let fixture: ComponentFixture<CustomerAttributeDetailComponent>;
        let service: CustomerAttributeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EshopTestModule],
                declarations: [CustomerAttributeDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    CustomerAttributeService,
                    EventManager
                ]
            }).overrideComponent(CustomerAttributeDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CustomerAttributeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CustomerAttributeService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new CustomerAttribute(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.customerAttribute).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
