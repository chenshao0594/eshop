import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { EshopTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { DigitalProductDetailComponent } from '../../../../../../main/webapp/app/entities/digital-product/digital-product-detail.component';
import { DigitalProductService } from '../../../../../../main/webapp/app/entities/digital-product/digital-product.service';
import { DigitalProduct } from '../../../../../../main/webapp/app/entities/digital-product/digital-product.model';

describe('Component Tests', () => {

    describe('DigitalProduct Management Detail Component', () => {
        let comp: DigitalProductDetailComponent;
        let fixture: ComponentFixture<DigitalProductDetailComponent>;
        let service: DigitalProductService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EshopTestModule],
                declarations: [DigitalProductDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    DigitalProductService,
                    EventManager
                ]
            }).overrideComponent(DigitalProductDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DigitalProductDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DigitalProductService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new DigitalProduct(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.digitalProduct).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
