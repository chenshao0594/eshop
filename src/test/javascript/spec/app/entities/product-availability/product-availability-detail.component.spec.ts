import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { EshopTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ProductAvailabilityDetailComponent } from '../../../../../../main/webapp/app/entities/product-availability/product-availability-detail.component';
import { ProductAvailabilityService } from '../../../../../../main/webapp/app/entities/product-availability/product-availability.service';
import { ProductAvailability } from '../../../../../../main/webapp/app/entities/product-availability/product-availability.model';

describe('Component Tests', () => {

    describe('ProductAvailability Management Detail Component', () => {
        let comp: ProductAvailabilityDetailComponent;
        let fixture: ComponentFixture<ProductAvailabilityDetailComponent>;
        let service: ProductAvailabilityService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EshopTestModule],
                declarations: [ProductAvailabilityDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ProductAvailabilityService,
                    EventManager
                ]
            }).overrideComponent(ProductAvailabilityDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ProductAvailabilityDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProductAvailabilityService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ProductAvailability(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.productAvailability).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
