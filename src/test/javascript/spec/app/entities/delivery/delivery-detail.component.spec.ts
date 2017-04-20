import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { EshopTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { DeliveryDetailComponent } from '../../../../../../main/webapp/app/entities/delivery/delivery-detail.component';
import { DeliveryService } from '../../../../../../main/webapp/app/entities/delivery/delivery.service';
import { Delivery } from '../../../../../../main/webapp/app/entities/delivery/delivery.model';

describe('Component Tests', () => {

    describe('Delivery Management Detail Component', () => {
        let comp: DeliveryDetailComponent;
        let fixture: ComponentFixture<DeliveryDetailComponent>;
        let service: DeliveryService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EshopTestModule],
                declarations: [DeliveryDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    DeliveryService,
                    EventManager
                ]
            }).overrideComponent(DeliveryDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DeliveryDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DeliveryService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Delivery(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.delivery).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
