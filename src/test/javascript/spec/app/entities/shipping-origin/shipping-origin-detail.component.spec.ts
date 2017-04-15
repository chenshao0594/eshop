import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { EshopTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ShippingOriginDetailComponent } from '../../../../../../main/webapp/app/entities/shipping-origin/shipping-origin-detail.component';
import { ShippingOriginService } from '../../../../../../main/webapp/app/entities/shipping-origin/shipping-origin.service';
import { ShippingOrigin } from '../../../../../../main/webapp/app/entities/shipping-origin/shipping-origin.model';

describe('Component Tests', () => {

    describe('ShippingOrigin Management Detail Component', () => {
        let comp: ShippingOriginDetailComponent;
        let fixture: ComponentFixture<ShippingOriginDetailComponent>;
        let service: ShippingOriginService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EshopTestModule],
                declarations: [ShippingOriginDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ShippingOriginService,
                    EventManager
                ]
            }).overrideComponent(ShippingOriginDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ShippingOriginDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ShippingOriginService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ShippingOrigin(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.shippingOrigin).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
