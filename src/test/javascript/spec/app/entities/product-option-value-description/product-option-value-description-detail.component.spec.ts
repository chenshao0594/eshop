import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { EshopTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ProductOptionValueDescriptionDetailComponent } from '../../../../../../main/webapp/app/entities/product-option-value-description/product-option-value-description-detail.component';
import { ProductOptionValueDescriptionService } from '../../../../../../main/webapp/app/entities/product-option-value-description/product-option-value-description.service';
import { ProductOptionValueDescription } from '../../../../../../main/webapp/app/entities/product-option-value-description/product-option-value-description.model';

describe('Component Tests', () => {

    describe('ProductOptionValueDescription Management Detail Component', () => {
        let comp: ProductOptionValueDescriptionDetailComponent;
        let fixture: ComponentFixture<ProductOptionValueDescriptionDetailComponent>;
        let service: ProductOptionValueDescriptionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EshopTestModule],
                declarations: [ProductOptionValueDescriptionDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ProductOptionValueDescriptionService,
                    EventManager
                ]
            }).overrideComponent(ProductOptionValueDescriptionDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ProductOptionValueDescriptionDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProductOptionValueDescriptionService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ProductOptionValueDescription(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.productOptionValueDescription).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
