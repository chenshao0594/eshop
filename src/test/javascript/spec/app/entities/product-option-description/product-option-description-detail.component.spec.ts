import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { EshopTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ProductOptionDescriptionDetailComponent } from '../../../../../../main/webapp/app/entities/product-option-description/product-option-description-detail.component';
import { ProductOptionDescriptionService } from '../../../../../../main/webapp/app/entities/product-option-description/product-option-description.service';
import { ProductOptionDescription } from '../../../../../../main/webapp/app/entities/product-option-description/product-option-description.model';

describe('Component Tests', () => {

    describe('ProductOptionDescription Management Detail Component', () => {
        let comp: ProductOptionDescriptionDetailComponent;
        let fixture: ComponentFixture<ProductOptionDescriptionDetailComponent>;
        let service: ProductOptionDescriptionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EshopTestModule],
                declarations: [ProductOptionDescriptionDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ProductOptionDescriptionService,
                    EventManager
                ]
            }).overrideComponent(ProductOptionDescriptionDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ProductOptionDescriptionDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProductOptionDescriptionService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ProductOptionDescription(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.productOptionDescription).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
